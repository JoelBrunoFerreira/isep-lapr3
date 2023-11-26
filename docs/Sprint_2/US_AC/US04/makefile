# A Makefile with variables and suffix rules

# variables
INCLUDES = asm.h
SOURCES = main.c sort_array.s
OBJFILES = main.o sort_array.o
EXEC = sort_array

# Suffix rules
.SUFFIXES : .c .o
.SUFFIXES : .s .o

# How to build an object .c from a code file .o ; $< -- file name
.c.o:
	gcc -g -Wall -Wextra -fanalyzer -c $<
	
# How to build an object .o from a code file .s ; $< -- file name
.s.o:
	gcc -g -Wall -Wextra -fanalyzer -c $<

#build main program
${EXEC}: ${OBJFILES}
	gcc ${OBJFILES} -z noexecstack -o ${EXEC}

${OBJFILES}: ${SOURCES} ${INCLUDES}


#exec main program
run: ${EXEC}
	./${EXEC}


clean:
	rm -f ${OBJFILES} ${EXEC}

# EOF #
#######