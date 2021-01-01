package com;

import com.modeliosoft.modelio.javadesigner.annotations.objid;

@objid ("db400367-9877-4795-8881-09c98320fca7")
public class CardPayment {
    @objid ("1101ab1c-44a9-44c0-8a11-87bf678b78ee")
    public boolean bankApproved;

    @objid ("22267646-df14-415e-ab8f-c4abe49187ff")
    public Customer customer;

    @objid ("3c4efd5c-b743-4359-8f10-b2131a6ff09e")
    public void totalAmount(final float total) {
    }

    @objid ("6443c491-2b3b-4b4f-988b-d4dac992163e")
    public void verificationScreen(final boolean bankApproved) {
    }

}
