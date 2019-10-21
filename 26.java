public int removeDuplicates(int[] A) {
    int len = A.length;
    if (A.length <= 1) return len;
    
    int i = 0;
    int j = 1;

    for (; j < A.length; j++) {
        int val = A[i];
        if(val == A[j]) {
            len--;
            continue;
        }
        else
            A[++i] = A[j];
    }
    
    return len;
}