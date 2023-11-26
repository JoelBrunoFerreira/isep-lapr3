.section .text
.global sort_array # void sort_array(int* ptr, int num);

sort_array:    # %rdi has ptr, %esi num
    # prologue #
    pushq %rbp
    movq %rsp, %rbp
    # # # # # # #

    movq %rdi, %r8      # copy address of array to %r8
    movl %esi, %r9d     # copy length of array to %r9d
    subl $1, %r9d       # sub 1 to %r9d (length-1)
    
    cmpl $0, %r9d
    jle end

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
    jz end					# Jump to end if %r9d is zero

    jmp outer_loop			# Jump back to outer_loop

end:
    # epilogue #
    movq %rbp, %rsp
    popq %rbp
    # # # # # # #
ret
	