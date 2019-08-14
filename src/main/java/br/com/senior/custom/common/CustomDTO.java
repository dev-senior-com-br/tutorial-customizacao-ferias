package br.com.senior.custom.common;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Classe base para DTOs customizados habilitados. Base class to custom-enabled DTOs.
 */
public class CustomDTO {
    private Map<String, Object> custom;

    /**
     * Atribui um valor a um campo customizado. Sets the value of a custom field.
     * @param field Nome do campo name of the field
     * @param value Valor do campo value of the field
     */
    public void setCustom(String field, Object value) {
        if (custom == null) {
            custom = new HashMap<>();
        }
        custom.put(field, value);
    }

    /**
     * Retorna o valor de um campo customizado. Returns the value of the custom field.
     *
     * @param field Nome do campo name of the field
     * @return Valor do campo, ou <code>null</code> se o valor do campo não estiver presente value of the field, or <code>null</code> if the field is not present
     */
    public Object getCustom(String field) {
        if (custom != null) {
            return custom.get(field);
        }
        return custom;
    }

    /**
     * Retorna todos os campos customizados mapeados Returns the map with all custom fields set.
     *
     * @return {@link Map} com os campos customizados map with the custom fields
     */
    public Map<String, Object> getCustom() {
        return custom != null ? Collections.unmodifiableMap(custom) : Collections.emptyMap();
    }

}
