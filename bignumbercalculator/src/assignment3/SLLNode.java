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
public class SLLNode {
    protected Object element;
    protected SLLNode succ;
    public SLLNode(Object ele, SLLNode n){ // constructor
        element = ele;
        succ = n;
    }
}