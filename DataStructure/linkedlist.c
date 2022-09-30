#include <stdio.h>
#include <stdlib.h>

#define CREATE 1
#define APPEND 2
#define PUSH 3
#define POP 4
#define INSERTAT 5
#define VALUEAT 6
#define DELETEAT 7
#define END 8

typedef int element;
typedef struct ListNode {
	element data;
	struct ListNode* link;
} ListNode;

ListNode* create(int start, int end, int itrv) {

	int element_cnt = (int)(((end - start) / itrv) + 1);

	ListNode* head = (ListNode*)malloc(sizeof(ListNode));

	head->data = start;
	head->link = NULL;

	for (int i = 0; i < element_cnt; i++) {
		ListNode* p = (ListNode*)malloc(sizeof(ListNode));
		p->data = start;
		p->link = head;
		head = p;
		start += itrv;
	}
	return head;
}
void insert(ListNode* head, ListNode* q, element e) {

	ListNode *p = (ListNode*)malloc(sizeof(ListNode));
	p->data = e;
	p->link = head;
	head = p;

	return head;
}
void print_list(const char* cmd, ListNode* head) {
	printf("%s  [ ", cmd);
	
	ListNode* p = head;

	while (p->link != NULL) {
		printf("%d ", p->data);
		p = p->link;
	}
	printf(" ]\n");
}
ListNode* get_last_node(ListNode* head);
ListNode* get_at(ListNode* head, int at);
ListNode* reverse(ListNode* head);

const char* cmdstr[] = { "create(s, e, i)", "append", "push", "pop", "insertat", "valueat", "deleteat", "end" };


int main() {
	element e;

	for (int i = 0; i < 8; i++) {
		printf("(%d) %-9s", i + 1, cmdstr[i]);

		if (i % 3 == 2)
			printf("\n");
	}
	printf("\n");

	ListNode* head = NULL, * result = NULL, * pre;

	int menu = 1;
	int start = 10, end = 100, itrv = 12, at;
	head = create(start, end, itrv);
	print_list(cmdstr[menu - 1], head);

	while (menu != END) {
		printf(">> ");
		scanf_s("%d", &menu);

		switch (menu) {
		case CREATE:
			scanf_s("%d %d %d", &start, &end, &itrv);
			head = create(start, end, itrv);
			break;
		case APPEND:
			scanf_s("%d", &e);
			insert(head, NULL, e);
			break;
		case PUSH:
			scanf_s("%d", &e);
			head = insert_first(head, e);
			break;
		case INSERTAT:
			break;
		case VALUEAT:
			break;
		case DELETEAT:
			break;
		case END:
			break;
		}
		print_list(cmdstr[menu - 1], head);
	}
}
