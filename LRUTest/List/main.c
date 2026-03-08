#include "list.h"
#include <stdio.h>
#include <malloc.h>
#include <string.h>

// generic list
typedef struct {
	int id;
	char* name;
	int age;
} Student;

void destruct_student(Student* s) {
	free(s->name);
	free(s);
}

void print_info(Student* s) {
	printf("id: %d, name: %s, age: %d\n", s->id, s->name, s->age);
}

int main() {
	List* l = create_list();
	Destructor d = destruct_student;
	int cmd;
	Student* s;

	while (1) {
		printf("Type command [1: push, 2: pop, 3: peek, 4: print, 5: exit]: ");
		scanf("%d", &cmd);

		switch (cmd) {
		case 1:
			printf("Select position [1: front, -1: back]: ");
			scanf("%d", &cmd);

			s = (Student*)malloc(sizeof(Student));
			printf("id: ");
			scanf("%d", &s->id);
			while (getchar() != '\n');

			printf("name: ");
			s->name = calloc(100, sizeof(char));
			fgets(s->name, 100, stdin);

			s->name[strcspn(s->name, "\n")] = '\0';
			printf("age: ");
			scanf("%d", &s->age);
	
			if (cmd == 1)
				push_front(l, s);
			else
				push_back(l, s);
			break;
		case 2:
			printf("Select position [1: front, -1: back]: ");
			scanf("%d", &cmd);

			s = (cmd == 1 ? pop_front(l) : pop_back(l));
			if (s) {
				printf("Success. Element popped.\n");
				print_info(s);
			}
			else
				printf("No element exists in list.\n");
			break;
		case 3:
			printf("Select position [1: front, -1: back]: ");
			scanf("%d", &cmd);

			s = (cmd == 1 ? get_first(l) : get_last(l));
			if (s) {
				printf("Success.\n");
				print_info(s);
			}
			else
				printf("No element exists in list.\n");
			break;
		case 4:
			printf("Print all elements in list..\n");
			if (!is_empty(l)) {
				Node* curr = l->head;
				while (curr) {
					print_info(curr->value);
					curr = curr->next;
				}
			}
			break;
		case 5:
			destruct_list(l, d);
			return 0;
		}
	}
	return 0;
}
