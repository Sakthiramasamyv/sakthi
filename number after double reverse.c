bool isSameAfterReversals(int num) {
int TEMP = num,rev1=0,rev2=0;
   while(num>0)
   {
    rev1=rev1*10+num%10;
    num=num/10;
   }
   while(rev1>0)
   {
    rev2=rev2*10+rev1%10;
    rev1=rev1/10;
   }
    if(rev2==TEMP)
    return true;
    else
    return false;
}