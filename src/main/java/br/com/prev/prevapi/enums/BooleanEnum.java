package br.com.prev.prevapi.enums;

import lombok.Getter;

@Getter
public enum BooleanEnum {

    SIM("S"),
    NAO("N");

    public String codigo;

    BooleanEnum(String codigo) {
        this.codigo = codigo;
    }

    public static String get(Boolean value) {
        if (value == null) {
            return null;
        }

        return value ? BooleanEnum.SIM.getCodigo() : BooleanEnum.NAO.getCodigo();
    }
}
