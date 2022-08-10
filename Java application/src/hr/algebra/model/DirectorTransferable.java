/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.model;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

/**
 *
 * @author Korsnik
 */
public class DirectorTransferable implements Transferable {

    public static final DataFlavor DIRECTOR_FLAVOR = new DataFlavor(Director.class, "Director");
    private static final DataFlavor[] SUPPORTED_FLAVORS = {DIRECTOR_FLAVOR};

    private final Director director;

    public DirectorTransferable(Director director) {
        this.director = director;
    }

    @Override
    public DataFlavor[] getTransferDataFlavors() {
        return SUPPORTED_FLAVORS;
    }

    @Override
    public boolean isDataFlavorSupported(DataFlavor flavor) {
        return DIRECTOR_FLAVOR.equals(flavor);
    }

    @Override
    public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
       if (isDataFlavorSupported(flavor)) {
            return director;
        }
        throw new UnsupportedFlavorException(flavor);        
    }

}
