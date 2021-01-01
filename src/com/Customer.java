package com;

import java.util.ArrayList;
import java.util.List;
import com.modeliosoft.modelio.javadesigner.annotations.objid;

@objid ("1ef4435d-dbaf-4462-a3c0-00a3526dcb23")
public class Customer implements IKiosk {
    @objid ("724b295c-0dba-4992-a234-396427736e0a")
    public List<Stock> stock = new ArrayList<Stock> ();

    @objid ("a781a9ad-83e4-4742-a961-838f8dbcb2cc")
    public void scannedItems(final List<Integer> itemCode) {
    }

    @objid ("5dddcb91-01bb-4f1e-8d1b-ba014f11235e")
    public void payMethod() {
    }

}
