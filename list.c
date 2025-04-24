struct ListNode* reverseList(struct ListNode* head) {
    struct ListNode*head1=NULL;
   
    while(head!=NULL)
    {
         struct ListNode* newnode=(struct ListNode*)malloc(sizeof(struct ListNode));
        newnode->val=head->val;
        newnode->next=head1;
        head1=newnode;
        head=head->next;
    }
    return head1;
    
}