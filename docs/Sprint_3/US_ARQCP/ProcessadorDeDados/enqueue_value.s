.section .text
.global enqueue_value # void enqueue_value(int* array, int length, int* read, int* write, int value);

enqueue_value:    # %rdi has array, %esi array length, %rdx has read, %rcx has write, %r8d has value
    # prologue #
    pushq %rbp
    movq %rsp, %rbp
    # # # # # # #
    movq $0, %r9
    
    #verifica se o array tem length válido
    cmpl $0, %esi
    jle end
    # verifica se o reader está dentro do range 0 a length-1
    movl (%rdx), %r9d
    cmpl $0, %r9d
    jl end
    cmpl %esi, %r9d
    jge end
    # verifica se o writer está dentro do range 0 a length-1
    movl (%rcx), %r10d
    cmpl $0, %r10d
    jl end
    cmpl %esi, %r10d
    jge end  
    # colocar o valor pretendido na posição onde está o writer
    movl %r8d, (%rdi, %r10, 4)
    incl %r10d  # incrementa o writer
    #verificar as posições do reader e writer
    check_position:
        # ver se o reader está no "fim" do array
        cmpl %esi, %r9d
        jge circulate_r
        # ver se o writer está no "fim" do array
        cmpl %esi, %r10d
        jge circulate_w
        # ver se o writer chegou ao reader
        cmpl %r9d, %r10d
        je same_rw
        # salta para o pre-fim se tudo ok
        jmp pre_end
    
    circulate_r:
        movl $0, %r9d
        jmp check_position
    
    circulate_w:  
        movl $0, %r10d
        jmp check_position

    same_rw:
        incl %r9d
        cmpl $0, (%rdi, %r9, 4)
        je empty
        jmp check_position
    empty:
        decl %r9d
# atualizar os valores no endereços do reader e writer 
pre_end:   
    movl %r9d, (%rdx)
    movl %r10d, (%rcx)

end:
    # epilogue #
    movq %rbp, %rsp
    popq %rbp
    # # # # # # #
ret
	