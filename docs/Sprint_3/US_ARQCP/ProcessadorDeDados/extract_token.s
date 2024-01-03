.section .text
.global extract_token # int extract_token(char* input, char* token, int* output);

extract_token:    # %rdi has input, %rsi token, %rdx has output ptr
  # prologue #
    pushq %rbp
    movq %rsp, %rbp
    # # # # # # # #
    # local variable
    subq $8, %rsp
    movl $10, -8(%rbp)
    # # # # # # # #
    movl (%rdx), %eax
    movl %eax, -4(%rbp)

    movq $0, %rax
    movq $0, %rcx
    movq $0, %r8
    movq $0, %r9
    
    pushq %rdx

    loop:
        movb (%rdi), %cl    # al = s d (input = sensor_id:8#type:atmospheric_temperature#value:21.60#unit:celsius#time:2470030 )
        cmpb $0, %cl        
        je end
        movb (%rsi, %r8, 1), %r9b    # ah = s d (token = sensor_id)  
        cmpb $0, %r9b
        je end
        cmpb %cl, %r9b       # ah = al ?
        je found            
        incq %rdi
        jmp loop
    
    found:
        incq %rdi
        incq %r8
        movb (%rdi), %cl    
        movb (%rsi,%r8,1), %r9b
        cmpb %cl, %r9b       
        je end_found
        cmpb %cl, %r9b       
        je found 
        movq $0, %r8
        jmp loop
    end_found:
        cmpb $':', %r9b
        je get_value  
        jmp found
    
    get_value:
        incq %rdi       
        movb (%rdi), %cl
        cmpb $'#', %cl
        je pre_end
        cmpb $'.', %cl
        je get_value 
        cmpb $0, %cl
        je pre_end
        cmpb $'\n', %cl
        je pre_end
        imull -8(%rbp)
        subb $48, %cl
        addl %ecx, %eax       
        jmp get_value
           
pre_end:
    popq %rdx
    movl %eax, (%rdx)

    cmpl %eax, -4(%rbp)
    je return_zero
    movl $1, %eax
    jmp end
return_zero:
    movl $0, %eax
end:
    # epilogue #
    movq %rbp, %rsp
    popq %rbp
    # # # # # # #
ret
	