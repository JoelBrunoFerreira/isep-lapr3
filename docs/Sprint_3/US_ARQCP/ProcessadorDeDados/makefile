# A Makefile with variables and suffix rules

# variables
INCLUDES = header.h
SOURCES = main.c functions.c extract_token.s enqueue_value.s move_num_vec.s fnc_mediana.s
OBJFILES = main.o functions.o extract_token.o enqueue_value.o move_num_vec.o fnc_mediana.o
EXEC = ProcessadorDeDados

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
	./${EXEC} /dev/ttyACM0 config.txt intermedio 50
#exec main program
run2: ${EXEC}
	./${EXEC} coletor.txt config.txt intermedio 50
		
clean:
	rm -f ${OBJFILES} ${EXEC}

# EOF #
#######