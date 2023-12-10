package org.isep.Domain;

import java.io.Serializable;

public enum FertirregaMix implements Serializable {
    MIX1("Epsotop/K+S(1.5 kg/ha) + Solusop/K+S(2.5 kg/ha) + Floracal/Plymag(1.7 l/ha)"),
    MIX2("MOL/Tecniferti(60 l/ha) + AllGrip/AsfertGlobal(2 l/ha)"),
    MIX3("Cuperdem/AsfertGlobal(2.2 l/ha)");

    public final String label;
    private FertirregaMix(String label) {
        this.label = label;
    }
    @Override
    public String toString() {
        return label;
    }


}
