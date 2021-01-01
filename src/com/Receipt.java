package com;

import java.util.List;
import com.modeliosoft.modelio.javadesigner.annotations.objid;

@objid ("4651aaa3-1b19-45ed-92d5-3da7f71652e4")
public class Receipt {
    @objid ("66ff90de-2515-4aeb-bfcc-5e0a37552484")
    public String typePayment;

    @objid ("3c1e0908-f32c-4eba-8a93-f6f7251869ca")
    public CardPayment cardPayment;

    @objid ("19c9bff8-17e6-432c-8cd6-4e7672313038")
    public CashPayment cashPayment;

    @objid ("2f8ef35a-cac5-48e6-91e8-6f98b9382f5f")
    public void purchasedItems(final List<String> ItemName) {
    }

}
