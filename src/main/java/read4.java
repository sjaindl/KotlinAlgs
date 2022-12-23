

public class read4 extends Reader4 {
    /**
     * @param buf Destination buffer
     * @param n   Number of characters to read
     * @return    The number of actual characters read
     */
    
    static char[] myBuf = new char[0];
    
    public int read(char[] buf, int n) {  
        if (n <= 0) {
            //buf = new char[0];
            return 0;
        }
        
        int oriN = n;
        
        if (n <= myBuf.length) {
            for (int index = 0; index < n; index++) {
                buf[index] = myBuf[index];
            }
            
            int remainingChars = myBuf.length - n;
            char[] temp = myBuf;
            myBuf = new char[remainingChars];
            for (int index = 0; index < remainingChars; index++) {
                myBuf[index] = temp[remainingChars + index];
            }
            
            return n;
        }
        
        if (myBuf.length > 0) {
            for (int index = 0; index < n; index++) {
                buf[index] = myBuf[index];
            }
            n -= myBuf.length;
            myBuf = new char[0];
        }
        
        int read = 0;
        while (n >= read) {
            int curRead = read4(buf);
            read += curRead;
            n = Math.max(0, n - curRead);
            if (curRead == 0) break;
        }
        
        if (read > oriN) {
            myBuf = new char[read - oriN];
            for (int index = 0; index < read - oriN; index++) {
                myBuf[index] = buf[oriN + index];
            }
        }
        
        for (int index = oriN; index < read; index++) {
            buf[index] = '\0';
        }
        
        return n;
    }
}