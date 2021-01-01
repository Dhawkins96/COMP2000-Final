package com;

import com.modeliosoft.modelio.javadesigner.annotations.objid;

@objid ("2d473026-f226-4119-b950-f86a6e887fe1")
public class CashPayment {
    @objid ("4b9bbf7c-b897-48d3-8eaa-0687fe4edb5c")
    public float cashGiven;

    @objid ("040aa46d-0e8e-41f7-afc7-a543ae185c17")
    public Customer customer;

    @objid ("58152340-2554-4c73-8bb8-2ca4f4528d38")
    public void totalAmount(final float total) {
    }

    @objid ("f489b697-f3ab-4fd5-9b53-690398431ba1")
    public void cashChange() {
    }

}
