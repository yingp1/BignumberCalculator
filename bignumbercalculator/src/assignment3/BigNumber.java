/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment3;
/**
 *
 * @author kreul
 */
public class BigNumber {
    
    private SLL digits1 = new SLL(); // Integer portion
    private SLL digits2 = new SLL(); // Decimal portion
    private int decimalPointPosition;
    public boolean negative = false;
    public boolean inputErr = false;
    
    public BigNumber(String s)
    {
        boolean hasDecimal = false;
        // Check if empty
        if(s.isEmpty()) {
                inputErr = true;
                return;
            }
                    
        // Detect sign and check input
        if(s.charAt(0) == '-' && s.length() > 1) {
            negative = true;
            s = s.substring(1);
            // Delete unnecessary zeros before numbers if it has
            while(s.charAt(0) == '0' && s.length() > 1)
                s = s.substring(1);
            if(s.charAt(0) == '.')
                s = "0" + s;
            // Delete unnecessary zeros after numbers if it has
            int pointer = s.length();
            while(s.contains(".") && s.charAt(pointer - 1) == '0') {
                pointer = pointer - 1;
            }
            if(s.charAt(pointer - 1) == '.')
                pointer = pointer - 1;
            s = s.substring(0, pointer);
            // Use regex to check input
            if(!s.matches("^\\d*(\\.\\d+)?$")) {
                inputErr = true;
                return;
            }
        }
        // If the string only contains single "-" or "."
        else if(s.charAt(0) == '-' && s.length() == 1 || s.charAt(0) == '.' && s.length() == 1) {
            inputErr = true;
            return;
        }
        else {
            // Delete unnecessary zeros before numbers if it has
            while(s.charAt(0) == '0' && s.length() > 1)
                s = s.substring(1);
            if(s.charAt(0) == '.')
                s = "0" + s;
            // Delete unnecessary zeros after numbers if it has
            int pointer = s.length();
            while(s.contains(".") && s.charAt(pointer - 1) == '0') {
                pointer = pointer - 1;
            }
            if(s.charAt(pointer - 1) == '.')
                pointer = pointer - 1;
            s = s.substring(0, pointer);
            // Use regex to check input
            if(!s.matches("^\\d*(\\.\\d+)?$")) {
                inputErr = true;
                return;
            }
        }
        
        // Read the input and store the integer portion and decimal portion seperately into SSLs
        for(int x = 1; x <= s.length(); x++)
        {
            if(s.charAt(x-1) == '.')
            {
                hasDecimal = true;
                decimalPointPosition = x - 1;
                continue;
            }
            if(hasDecimal == true)
            {               
                SLLNode node = new SLLNode(s.charAt(x-1), null);
              //System.out.println(node.element.toString()); // Test code
            
                if(x == decimalPointPosition + 2){
                    digits2.setFirst(node);
                }
                else    
                    digits2.insertAtFront(node);
            }
            else{
                SLLNode node = new SLLNode(s.charAt(x-1), null);
               //System.out.println(node.element.toString()); //Test code
           
                if(x == 1)
                    digits1.setFirst(node);
                else    
                    digits1.insertAtFront(node);
            }
            
        }
    }
    
    // Return the integer portion
    public SLL getDigits1() {
        return this.digits1;
    }
    
    // Return the decimal portion
    public SLL getDigits2() {
        return this.digits2;
    }
    
    // Return absolute value
    public BigNumber abs() {
        BigNumber n = new BigNumber("");
        n.digits1 = this.digits1;
        n.digits2 = this.digits2;
        n.negative = this.negative;
        if(n.negative)
            n.negative = false;
        return n;
    }
    
    // Apply scientific notation if length > MAX_LENGTH
    public String sciNot(String str){
        int MAX_LENGTH = 43;
        int count = 0;
        String sci_str = "";
        // If positive and the string is like 0.000XXXX
        if(str.length() > MAX_LENGTH && str.contains(".") && str.charAt(0) == '0') {
            for(int i = 2; i < str.length(); i++)
                if(str.charAt(i) != '0') {
                    count = i - 1;
                    break;
                }
            System.out.println(count);
            sci_str = sci_str + str.charAt(count + 1) + ".";
            for(int i = count + 2; i < Math.min(MAX_LENGTH + count - 4, str.length()); i++)
                sci_str = sci_str + str.substring(i, i+1);
            sci_str = sci_str + "e" + "-" + Integer.toString(count);
        }
        // If positive and the string is like XXX.XXXX
        else if(str.length() > MAX_LENGTH && str.contains(".") && str.charAt(0) != '0' && str.charAt(0) != '-') {
            for(int i = 0; i < str.length(); i++)
                if(str.charAt(i) == '.') {
                    count = i - 1;
                    str = str.substring(0, i) + str.substring(i + 1);
                }
            sci_str = sci_str + str.charAt(0) + ".";
            for(int i = 1; i < MAX_LENGTH - 4; i++)
                sci_str = sci_str + str.substring(i, i+1);
            sci_str = sci_str + "e" + "+" +Integer.toString(count);
        }
        // If positive and the string is like XXXXXXX
        else if(str.length() > MAX_LENGTH && !str.contains(".") && str.charAt(0) != '-') {
            count = str.length() - 1;
            sci_str = sci_str + str.charAt(0) + ".";
            for(int i = 1; i < MAX_LENGTH - 4; i++)
                sci_str = sci_str + str.substring(i, i+1);
            sci_str = sci_str + "e" + "+" +Integer.toString(count);
        }
        // If negative and the string is like 0.000XXXX
        else if(str.length() > MAX_LENGTH && str.contains(".") && str.charAt(1) == '0' && str.charAt(0) == '-') {
            for(int i = 3; i < str.length(); i++)
                if(str.charAt(i) != '0') {
                    count = i - 2;
                    break;
                }
            sci_str = sci_str + str.charAt(count + 2) + ".";
            for(int i = count + 3; i < Math.min(MAX_LENGTH + count - 4, str.length()); i++)
                sci_str = sci_str + str.substring(i, i+1);
            sci_str = sci_str + "e" + "-" + Integer.toString(count);
        }
        // If negative and the string is like XXX.XXXX
        else if(str.length() > MAX_LENGTH && str.contains(".") && str.charAt(1) != '0' && str.charAt(0) == '-') {
            for(int i = 0; i < str.length(); i++)
                if(str.charAt(i) == '.') {
                    count = i - 2;
                    str = str.substring(0, i) + str.substring(i + 1);
                }
            sci_str = sci_str + "-" + str.charAt(1) + ".";
            for(int i = 2; i < MAX_LENGTH - 4; i++)
                sci_str = sci_str + str.substring(i, i+1);
            sci_str = sci_str + "e" + "+" +Integer.toString(count);
        }
        // If negative and the string is like XXXXXXX
        else if(str.length() > MAX_LENGTH && !str.contains(".") && str.charAt(0) == '-') {
            count = str.length() - 2;
            sci_str = sci_str + "-" +str.charAt(1) + ".";
            for(int i = 2; i < MAX_LENGTH - 4; i++)
                sci_str = sci_str + str.substring(i, i+1);
            sci_str = sci_str + "e" + "+" +Integer.toString(count);
        }
        else
            sci_str = str;
        return sci_str;
    }
    
    public String toString()
    {
        String bigNumber = "";
        
        // Turn the numbers into string
        if(digits2.getFirst() != null)
        {
            for(SLLNode curr = digits2.getFirst(); curr != null; curr = curr.succ) //loop until curr does not reference a node
            {
            String s = curr.element.toString();
            bigNumber = s + bigNumber;
            }
        
            bigNumber = "." + bigNumber;
        }
        
        for(SLLNode curr = digits1.getFirst(); curr != null; curr = curr.succ) //loop until curr does not reference a node
        {
            String s = curr.element.toString();
            bigNumber = s + bigNumber;
        }
        
        // Add sign to the number
        if(negative)
            bigNumber = "-" + bigNumber;
        return bigNumber;
    }
    
    // Basic operations (signs not include)
    public BigNumber add(BigNumber bn)
    {
        SLLNode digits1curr = this.digits1.getFirst();
        SLLNode digits1curr2 = bn.digits1.getFirst();
        SLLNode digits2curr = this.digits2.getFirst();
        SLLNode digits2curr2 = bn.digits2.getFirst();
        
        // Store the result
        SLL integerPart = new SLL();
        SLL decimalPart = new SLL();
        // Add integer part, use 48 + n in char to represent number n
        if(this.digits1.getLength() >= bn.digits1.getLength())
        {
            for(; digits1curr2 != null; digits1curr2 = digits1curr2.succ)
            {
                digits1curr.element = (char)((char)digits1curr2.element + (char)digits1curr.element - '0');
                digits1curr = digits1curr.succ;
            }
            integerPart.setFirst(this.digits1.getFirst());
        }
        else
        {
            for(; digits1curr != null; digits1curr = digits1curr.succ)
            {
                digits1curr2.element = (char)((char)digits1curr.element + (char)digits1curr2.element - '0');
                digits1curr2 = digits1curr2.succ;
            }
            integerPart.setFirst(bn.digits1.getFirst());
        }
        
        // Add decimal part, use 48 + n in char to represent number n
        if(this.digits2.getFirst() == null && bn.digits2.getFirst() == null);
        else if(this.digits2.getLength() >= bn.digits2.getLength())
        {
            for(int i = this.digits2.getLength() - bn.digits2.getLength(); i > 0; i--)
            {
                digits2curr = digits2curr.succ;
            }
            for(; digits2curr2 != null; digits2curr2 = digits2curr2.succ)
            {
                digits2curr.element = (char)((char)digits2curr2.element + (char)digits2curr.element - '0');
                digits2curr = digits2curr.succ;
            }
            decimalPart.setFirst(this.digits2.getFirst());
        }
        else
        {
            for(int i = bn.digits2.getLength() - this.digits2.getLength(); i > 0; i--)
            {
                digits2curr2 = digits2curr2.succ;
            }
            for(; digits2curr != null; digits2curr = digits2curr.succ)
            {
                digits2curr2.element = (char)((char)digits2curr.element + (char)digits2curr2.element - '0');
                digits2curr2 = digits2curr2.succ;
            }
            decimalPart.setFirst(bn.digits2.getFirst());
        }
        
        // If one digit >= 48 + 10, carry to the higher digit and subtract it by 10
        for(SLLNode here = decimalPart.getFirst(); here != null; here = here.succ)
        {
            if((char)here.element >= ('0' + 10) && here.succ != null)
            {
                here.element = (char)((char)here.element - 10);
                here.succ.element = (char)((char)here.succ.element + 1);
            }
            else if((char)here.element >= ('0' + 10) && here.succ == null)
            {
                here.element = (char)((char)here.element - 10);
                integerPart.getFirst().element = (char)((char)integerPart.getFirst().element + 1);
            }
            else;
        }
        
        for(SLLNode here = integerPart.getFirst(); here != null; here = here.succ)
        {
            if((char)here.element >= ('0' + 10) && here.succ != null)
            {
                here.element = (char)((char)here.element - 10);
                here.succ.element = (char)((char)here.succ.element + 1);
            }
            else if((char)here.element >= ('0' + 10) && here.succ == null)
            {
                here.element = (char)((char)here.element - 10);
                SLLNode newNode = new SLLNode('1', null);
                integerPart.insertAtFront(newNode);
            }
            else;
        }
        BigNumber result = new BigNumber("");
        result.digits1.setFirst(integerPart.getFirst());
        result.digits2.setFirst(decimalPart.getFirst());
        return result;
    }
    
    public BigNumber subtract(BigNumber bn)
    {
        int length1 = Math.max(bn.digits1.getLength(),digits1.getLength());  // larger length in Integer part
        int length2 = Math.max(bn.digits2.getLength(),digits2.getLength());  // larger length in Decimal part
 
        bn.digits1.modifyInt(length1);
        
        /*completion the decimal part*/
        digits2.modifyDec(length2);
        bn.digits2.modifyDec(length2);
        
        digits2.concatSLL(digits1);
        bn.digits2.concatSLL(bn.digits1);

        SLL temp = new SLL();
        int ad = 0;     // carry initialize = 0
        
        /*put the decimal and integer together*/
        SLLNode N1=digits2.getFirst();
        SLLNode N2=bn.digits2.getFirst();

        /*set the first*/
        int d2;
        int d;
        int d_succ;
        SLLNode cur;
        if(digits2.getLength() == 1 && bn.digits2.getLength() == 1)
        {
            d2 = Integer.parseInt(N2.element.toString());   // second input 
            d = Integer.parseInt(N1.element.toString());    // first input
            cur = new SLLNode(d-d2,null);   // current number
            temp.setFirst(cur);
        }
        else {
            d2 = Integer.parseInt(N2.element.toString());   // second input 
            d = Integer.parseInt(N1.element.toString())+10;    // first input
        
        
            d_succ = Integer.parseInt(N1.succ.element.toString())-1;   // make the succ minus 1
            N1.succ.element = Integer.toString(d_succ);
        
            cur = new SLLNode((d-d2+ad)%10,null);   // current number
            temp.setFirst(cur);
            ad = (d-d2+ad)/10;
            N2=N2.succ;
            N1=N1.succ;
        }
        
        /*Process*/
        for(;N1.succ!=null;N2=N2.succ,N1=N1.succ)  // start
        {                
            d2 = Integer.parseInt(N2.element.toString());  // the number from lower text field
             d = Integer.parseInt(N1.element.toString())+10;
                
             d_succ = Integer.parseInt(N1.succ.element.toString())-1;   // make the succ minus 1
             N1.succ.element = Integer.toString(d_succ); 
                
             cur = new SLLNode((d-d2+ad)%10,null);
             temp.insertAtFront(cur);
             ad = (d-d2+ad)/10;        
        }   
        
        /*First position for Integer part*/
        if(digits2.getLength() != 1 && bn.digits2.getLength() != 1){
            d2 = Integer.parseInt(N2.element.toString());  // the number from lower text field
            d = Integer.parseInt(N1.element.toString());

            cur = new SLLNode((d-d2+ad),null);
            temp.insertAtFront(cur);
        }
        
         /*record into dm*/
        int de = 0;    // for record decimal position
        String dm="";
        for(SLLNode dis=temp.getFirst();dis!=null;dis=dis.succ){
             de++;
             if(de == length1+1)
             dm = dm+".";                  
             dm=dm+dis.element.toString();
        }
        BigNumber result = new BigNumber(dm);
        return result;
    }
    
    public BigNumber multiply(BigNumber bn)
    {
        int i=digits2.getLength()+bn.digits2.getLength();
        
        // get input
        digits2.concatSLL(digits1);
        bn.digits2.concatSLL(bn.digits1);
        SLLNode N1=digits2.getFirst();
        SLLNode N2=bn.digits2.getFirst();

        SLL temp = new SLL();
        int rec1=0;
        SLLNode tp=null;
        for(;N2!=null;N2=N2.succ)
        {
            int d=Integer.parseInt(N2.element.toString());
            SLLNode cur=temp.getFirst();
            int ad=0;
            int rec2=0;
            N1=digits2.getFirst();
            for(;N1!=null;N1=N1.succ)
            {      
                int d2=Integer.parseInt(N1.element.toString());
                if(rec2+rec1==0){                
                    cur=new SLLNode((d*d2+ad)%10,null);
                    temp.setFirst(cur);
                    ad=(d*d2+ad)/10;
                    if(ad!=0 && N1.succ==null){
                        temp.insertAtEnd(ad);                
                    }                  
                }                   
                else if(rec1==0){                           
                    temp.insertAtEnd((d*d2+ad)%10);                       
                    ad=(d*d2+ad)/10;

                    if(ad!=0 && N1.succ==null){
                        temp.insertAtEnd(ad);                
                    }                            
                }
                else if(rec1!=0 && rec2 ==0){

                    for(int h=1;h<=rec1;h++){
                        if(cur.succ==null)
                            cur.succ=new SLLNode(0,null);
                        cur=cur.succ;
                    }                  
                    tp=cur;
                    int q=(int)tp.element;
                    temp.replace((d*d2+ad+q)%10,tp);
                    ad=(d*d2+ad+q)/10;
                    if(ad!=0 && N1.succ==null){
                        temp.insertAtEnd(ad);                               
                    }
                }
                else if(rec1!=0 && rec2!=0){
                    if(tp.succ==null)
                        tp.succ=new SLLNode(0,null);
                    tp=tp.succ;
                    int q=(int)tp.element;
                    temp.replace((d*d2+ad+q)%10,tp);
                    ad=(d*d2+ad+q)/10;
                    if(ad!=0 && N1.succ==null){
                        temp.insertAtEnd(ad);                
                    }                 
                }
                rec2++;                  
            }
            rec1++;        
        }
        int de=-1;
        String dm="";
        for(SLLNode dis=temp.getFirst();dis!=null;dis=dis.succ){
            de++;
            if(de==i && i!=0)
                dm="."+dm;
            dm=dis.element.toString()+dm;
        }
        BigNumber result = new BigNumber(dm);
        return result;
    }
    
    //Currently NOT AVAILABLE
    public BigNumber divide(BigNumber bn)
    {
        Divide_GA evo = new Divide_GA(this, bn);
        return evo.evolve(3000, 100, 6000, 0.5F);
    }
    
    // Advanced operations (Signs included)
    public String plus(BigNumber bn) {
        if(this.negative && bn.negative) {
            BigNumber result = this.abs().add(bn.abs());
            result.negative = true;
            return sciNot(result.toString());
        }
        else if(!this.negative && bn.negative && !this.lessThan(bn.abs())) {
            BigNumber result = this.subtract(bn.abs());
            result.negative = false;
            return result.toString();
        }
        else if(!this.negative && bn.negative && this.lessThan(bn.abs())) {
            BigNumber result = bn.abs().subtract(this);
            result.negative = true;
            return result.toString();
        }
        else if(this.negative && !bn.negative && this.abs().greaterThan(bn)) {
            BigNumber result = this.abs().subtract(bn);
            result.negative = true;
            return result.toString();
        }
        else if(this.negative && !bn.negative && !(this.abs().greaterThan(bn))) {
            BigNumber result = bn.subtract(this.abs());
            result.negative = false;
            return result.toString();
        }
        else {
            BigNumber result = this.add(bn);
            result.negative = false;
            return sciNot(result.toString());
        }
    }
    
    public String minus(BigNumber bn) {
        if(this.negative && !bn.negative) {
            BigNumber result = this.abs().add(bn);
            result.negative = true;
            return sciNot(result.toString());
        }
        else if(this.negative && bn.negative && this.abs().greaterThan(bn.abs())) {
            BigNumber result = this.abs().subtract(bn.abs());
            result.negative = true;
            return result.toString();
        }
        else if(this.negative && bn.negative && !(this.abs().greaterThan(bn.abs()))) {
            BigNumber result = bn.abs().subtract(this.abs());
            result.negative = false;
            return result.toString();
        }
        else if(!this.negative && !bn.negative && !this.lessThan(bn)) {
            BigNumber result = this.subtract(bn);
            result.negative = false;
            return result.toString();
        }
        else if(!this.negative && !bn.negative && this.lessThan(bn)) {
            BigNumber result = bn.subtract(this);
            result.negative = true;
            return result.toString();
        }
        else {
            BigNumber result = this.add(bn.abs());
            result.negative = false;
            return sciNot(result.toString());
        }
    }
    
    public String mult(BigNumber bn) {
        if(this.negative && bn.negative || !this.negative && !bn.negative) {
            BigNumber result = this.abs().multiply(bn.abs());
            result.negative = false;
            return sciNot(result.toString());
        }
        else {
            BigNumber result = this.abs().multiply(bn.abs());
            result.negative = true;
            return sciNot(result.toString());
        }
    }
    
    public String div(BigNumber bn) {
        // Denominator can't be zero
        if(this.negative && bn.negative || !this.negative && !bn.negative) {
            BigNumber result = this.abs().divide(bn.abs());
            result.negative = false;
            return result.toString();
            
        }
        else {
            BigNumber result = this.abs().divide(bn.abs());
            result.negative = true;
            return result.toString();
        }
        //return "Not available in beta! Copywright@ZH,AK,ZLN,PTY";
    }
    
    public boolean equals(BigNumber bn)
    {  
        SLLNode digits1curr = this.digits1.getFirst();
        SLLNode digits1curr2 = bn.digits1.getFirst();
        SLLNode digits2curr = this.digits2.getFirst();
        SLLNode digits2curr2 = bn.digits2.getFirst();
        
        if((this.digits1.getLength() != bn.digits1.getLength()))
            return false; 
        else if((this.digits2.getLength() != bn.digits2.getLength()))
            return false;
        else if(this.negative ^ bn.negative)
            return false;
        else 
            while(digits1curr != null)
            {
                Character c = (Character)digits1curr.element;
                Character c2 = (Character)digits1curr2.element;
                
                //if(digits1curr.element != digits1curr2.element)
                    //return false;
                if(c.compareTo(c2) != 0)
                    return false;
                else
                {
                digits1curr = digits1curr.succ;
                digits1curr2 = digits1curr2.succ;
                }
            }
           while(digits2curr != null)
            {
                Character c = (Character)digits2curr.element;
                Character c2 = (Character)digits2curr2.element;
                
                //if(digits2curr.element != digits2curr2.element)
                  //  return false;
                if(c.compareTo(c2) != 0)
                    return false;
                else
                {
                digits2curr = digits2curr.succ;
                digits2curr2 = digits2curr2.succ;
                }
            }
            return true;
    }
    
    public boolean greaterThan(BigNumber bn)
    {
        boolean isLarger = false;
        boolean isEqual = false;
        SLLNode digits1curr = this.digits1.getFirst();
        SLLNode digits1curr2 = bn.digits1.getFirst();
        
        //compare lengths and signs to determine if node by node comparison is necessary
        if(bn.negative && !this.negative)
            return true;
        else if(this.negative && !bn.negative)
            return false;
        else if (this.digits1.getLength() > bn.digits1.getLength() && this.negative == true)
            return false;
        else if (this.digits1.getLength() > bn.digits1.getLength())
            return true;
        else if (this.digits1.getLength() < bn.digits1.getLength() && this.negative == true)
            return true;
        else if (this.digits1.getLength() < bn.digits1.getLength())
            return false;
        else if(this.equals(bn))
            return false;
        else
        {
            while(digits1curr != null) //loop until end of list
            {
                Character c = (Character)digits1curr.element; //cast element to Character so we can use compareTo
                Character c2 = (Character)digits1curr2.element; //cast element to Character so we can use compareTo
                
                if(c.compareTo(c2) > 0)
                {
                    isLarger = true;
                    isEqual = false;
                }
                else if (c.compareTo(c2) == 0)
                {
                    isEqual = true;
                }
                else if(c.compareTo(c2) < 0)
                {
                    isLarger = false;
                    isEqual = false;         
                }         
                digits1curr = digits1curr.succ; //move to next node
                digits1curr2 = digits1curr2.succ;                      
            }
            if(isLarger)
                return true;
            else if (!isLarger && !isEqual)
                return false;
            else
            { //if integer portion of strings are equal, compare decimal portion
                if(this.digits2.getLength() >= bn.digits2.getLength())
                {
                    for(int i = 0; i < bn.digits2.getLength(); i++)
                    {
                        SLLNode digits2curr = this.digits2.getFirst();
                        SLLNode digits2curr2 = bn.digits2.getFirst();
                        for(int j = 1; j < this.digits2.getLength() - i; j++)
                        {
                            digits2curr = digits2curr.succ;
                        }
                        for(int j = 1; j < bn.digits2.getLength() - i; j++)
                        {
                            digits2curr2 = digits2curr2.succ;
                        }
                        Character c = (Character)digits2curr.element;
                        Character c2 = (Character)digits2curr2.element;

                        if(c.compareTo(c2) > 0)
                        {
                            return true;
                        }
                        else if (c.compareTo(c2) == 0)
                        {
                            isEqual = true;
                        }
                        else if(c.compareTo(c2) < 0)
                        {
                            return false;         
                        }
                    }
                    return true;
                }
                else
                {
                    for(int i = 0; i < this.digits2.getLength(); i++)
                    {
                        SLLNode digits2curr = this.digits2.getFirst();
                        SLLNode digits2curr2 = bn.digits2.getFirst();
                        for(int j = 1; j < this.digits2.getLength() - i; j++)
                        {
                            digits2curr = digits2curr.succ;
                        }
                        for(int j = 1; j < bn.digits2.getLength() - i; j++)
                        {
                            digits2curr2 = digits2curr2.succ;
                        }
                        Character c = (Character)digits2curr.element;
                        Character c2 = (Character)digits2curr2.element;

                        if(c.compareTo(c2) > 0)
                        {
                            return true;
                        }
                        else if (c.compareTo(c2) == 0)
                        {
                            isEqual = true;
                        }
                        else if(c.compareTo(c2) < 0)
                        {
                            return false;         
                        }
                    }
                    return false;
                }
            }
        }
    }
    
    public boolean lessThan(BigNumber bn)
    {
        if(this.greaterThan(bn) == false && this.equals(bn) == false)
            return true;
        else
            return false;
    }   
}
