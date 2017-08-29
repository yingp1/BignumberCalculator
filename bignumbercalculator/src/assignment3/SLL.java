/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment3;

/**
 *
 * @author ZheHuang
 */
public class SLL {
    private SLLNode first;
    
    public SLL(){
        this.first = null;
    }
    
    public void setFirst(SLLNode node){
        this.first = node;
    }
    
    public SLLNode getFirst(){
        return this.first;
    }
    
    public void printFirstToLast(){
        for(SLLNode curr = first; curr != null; curr = curr.succ){
            System.out.print(curr.element + " ");
        }
        System.out.println("");
    }
    
    public void insertAtFront(SLLNode node){
        node.succ=this.first;
        this.first=node;
    }
    
    public void insertAfter(Object obj, SLLNode node){
        for(SLLNode here=this.first;here!=null;here=here.succ){
            if(here.element.equals(obj))
            {
                node.succ=here.succ;
                here.succ=node;
                //break;
                return;
            }
        }
    }
    
    public void insertAtEnd(Object obj){
        SLLNode here = this.first;
        if(here == null){
            this.first = new SLLNode(obj, null);
        }
        else{
            for(; here.succ != null; here = here.succ);
            here.succ = new SLLNode(obj, null);
        }
    }
    
    public void concatSLL(SLL n){
        SLLNode node = n.getFirst();
        SLLNode here=this.first;
        if(here == null)    // base case
        {
            this.first=node;
        }
        else
        {
            for(;here.succ!=null;here=here.succ)  ;          
                here.succ=node; // put at the end of singly list          
        }       
    }
    
    public void delete(SLLNode del){
        SLLNode succ = del.succ;
        // If del is first node, change link in header
        if(del == first)
            first = succ;
        else{
            // Find predecessor and change its link
            SLLNode pred = first;
            while(pred.succ != del)
                pred = pred.succ;
            pred.succ = succ;
        }
    }
    
    public int getLength(){
        int count = 0;
        for(SLLNode here = this.first; here != null; here = here.succ){
            count++;
        }
        return count;
    }
    
    public void replace(Object obj,SLLNode node){
        node.element=obj;
    }
    
    void modifyInt(int length)
    {
        int i = length - this.getLength(); 
        for(int j = 0; j < i; j++)
        {
            this.insertAtEnd("0");
        }
    }
    
    void modifyDec(int length)
    {
        int i = length - this.getLength();
        for(int j = 0; j < i; j++)
        {
            this.insertAtFront(new SLLNode("0",null));
        }
    }
}
