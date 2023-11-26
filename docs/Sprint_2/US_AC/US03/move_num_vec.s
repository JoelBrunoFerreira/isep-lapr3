.section .text
.global move_num_vec # int move_num_vec(int* array, int length, int* read, int* write, int num, int* vec);

move_num_vec:    # %rdi has array, %esi array length, %rdx has read, %rcx has write, %r8d has num, %r9 has vec
    # prologue #
    pushq %rbp
    movq %rsp, %rbp
    # # # # # # #

    movq $0, %rax

    #verifica se o array tem length válido
    cmpl $0, %esi
    jle end
    # verifica se o reader está dentro do range 0 a length-1
    movl (%rdx), %r10d
    cmpl $0, %r10d
    jl end
    cmpl %esi, %r10d
    jge end
    # verifica se o writer está dentro do range 0 a length-1
    movl (%rcx), %r11d
    cmpl $0, %r11d
    jl end
    cmpl %esi, %r11d
    jge end  
    # verifica se o array esta vazio
    cmpl %r10d, %r11d
    je end
    # verifica se o array tem elementos suficientes para copiar
    cmpl %r8d, %esi
    jl end
    movl $1, %eax  
   
    pushq %rax  # guardar o valor de retorno 1.

    # copia os elementos para o array destino
    copy_elements_loop:
        cmpl $0, %r8d
        je pre_end
        decl %r8d

        movl (%rdi, %r10, 4), %eax
        movl %eax, (%r9)
        addq $4, %r9        # incrementa a posição no array destino, para receber o próximo int
        incl %r10d          # incrementa o index do reader

        cmpl %esi, %r10d    # verifica se o index ultrapassa o limite
        jl copy_elements_loop
    #reader ultrapassa limite, circular para "inicio"
    circulate_r:
        movl $0, %r10d
        cmpl %r10d, %r11d   #verifica se o reader e o writer estão no mesmo index, significa que já leu tudo
        jne copy_elements_loop    

pre_end:
    movl %r10d, (%rdx)  # guardar o valor atual do reader no endereço
    popq %rax

end:
    # epilogue #
    movq %rbp, %rsp
    popq %rbp
    # # # # # # #
ret
	