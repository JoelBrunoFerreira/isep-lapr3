# A Makefile with variables and suffix rules

# variables
INCLUDES = header.h
SOURCES = main.c functions.c
OBJFILES = main.o functions.o 
EXEC = SaidaDeDados	

# Suffix rules
.SUFFIXES : .c .o .s

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
	./${EXEC} intermedio output 60000 
	
clean:
	rm -f ${OBJFILES} ${EXEC}

# EOF #
#######