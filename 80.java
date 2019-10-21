public int removeDuplicates(int[] nums) {
    if (nums == null) return -1;
    int len = nums.length;
    if (len < 3) return len;
    
    int left = 0;
    int right = 1;
    
    int curr = nums[0];
    int prev = nums[0];
    
    int currCount = 1;
        
    while (right < len) {
        if (nums[right] == curr) {
            currCount ++;
        } else {
            prev = curr;
            curr = nums[right];
            currCount = 1;
        }
        
        if (currCount <= 2) {
            left ++;
            nums[left] = nums[right]; // modify left element
            right ++;
            continue;
        } else {
            // left index and element remain
            right ++;
        }
    }
    
    return left + 1;
}