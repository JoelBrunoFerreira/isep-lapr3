.section .text
.global mediana # int mediana(int* vec, int num);

mediana:    # %rdi has vec, %esi has num (length of array)
    # prologue #
    pushq %rbp
    movq %rsp, %rbp
    # # # # # # #

    movq $0, %rax
    cmpl $0, %esi
    je end

    movl %esi, %eax
    cdq
    movl $2, %ecx
    idivl %ecx
    # %eax has (length/2)

    pushq %rax
    pushq %rdi
    pushq %rcx
    pushq %rsi
    

    call sort_array #sort the given array
    
    popq %rsi
    
    movl %esi, %edi

    call test_even
    
    popq %rcx
    popq %rdi
    
    cmpl $1, %eax
    je even_length

    popq %rax
    movl %esi, %eax
    cdq
    divl %ecx
    movl (%rdi, %rax, 4), %eax
    jmp end

even_length:
    popq %rax
    movl %esi, %eax
    cdq
    divl %ecx
    movl (%rdi, %rax, 4), %r8d
    decl %eax
    movl (%rdi, %rax, 4), %eax
    addl %r8d, %eax
    cdq
    idivl %ecx
end:
    # epilogue #
    movq %rbp, %rsp
    popq %rbp
    # # # # # # #
ret

.global test_even   # int test_even(int x);

    test_even:  # %rdi contains 'x'
    # prologue
    pushq %rbp
    movq %rsp, %rbp

        movl %edi, %eax # Move 'x' to eax
        cdq # Sign-extend eax into %edx

        
        movl $2, %ecx   # Divide eax by 2
        idivl %ecx

        cmpl $0, %edx   # Check the remainder in edx
        je even
        movl $0, %eax   # Return 0 if not even
        jmp end_even

    even:
        movl $1, %eax   # Return 1 if even
    
    end_even: 
    # epilogue
    movq %rbp, %rsp
    pop %rbp

    ret

sort_array:    # %rdi has ptr, %esi num
    # prologue #
    pushq %rbp
    movq %rsp, %rbp
    # # # # # # #

    movq %rdi, %r8      # copy address of array to %r8
    movl %esi, %r9d     # copy length of array to %r9d
    subl $1, %r9d       # sub 1 to %r9d (length-1)
    cmpl $0, %r9d
    jle end_sort
outer_loop:
    movq %r8, %rdi      # Set %rdi to the initial position of the array 
    movl %r9d, %esi     # Set %esi to the initial value of (length-1)
    
inner_loop:
    movl (%rdi), %edx       # Load the current integer value into %edx
    movl 4(%rdi), %ecx      # Load the next integer value into %ecx
    cmpl %ecx, %edx         # Compare the two integer values
    jle no_swap             # Jump to no_swap if %edx <= %ecx
    
    movl %edx, 4(%rdi)		# Swap current element to the next position
    movl %ecx, (%rdi)		# vice-versa

    no_swap:				
    decl %esi				# Decrement %esi
    jz end_inner_loop		# Jump to end_inner_loop if %esi is zero

    addq $4, %rdi			# Move to the next element in the array
    jmp inner_loop			# Jump back to inner_loop

end_inner_loop:				
    decl %r9d				# Decrement %r9d for the outer loop
    jz end_sort					# Jump to end if %r9d is zero

    jmp outer_loop			# Jump back to outer_loop

end_sort:
    # epilogue #
    movq %rbp, %rsp
    popq %rbp
    # # # # # # #
ret
	