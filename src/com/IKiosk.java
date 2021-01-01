package com;

import java.util.List;
import com.modeliosoft.modelio.javadesigner.annotations.objid;

@objid ("e80966e3-fab6-44d1-a442-2514a6b790cf")
public interface IKiosk {
    @objid ("80b327ba-1eb6-49ce-8e83-f07548a68937")
    void scannedItems(final List<Integer> itemCode);

}
