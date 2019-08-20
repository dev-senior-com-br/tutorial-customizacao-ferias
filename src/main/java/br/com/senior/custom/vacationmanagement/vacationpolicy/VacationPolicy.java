package br.com.senior.custom.vacationmanagement.vacationpolicy;


import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class VacationPolicy{

    /**
     * Id da política
     */
    public String id;

    /**
     * Nome da política
     */
    public String policyName;

    /**
     * Permitir solicitar o adiantamento do 13º salário
     */
    public Boolean allowThirteenthSalaryAnticipation;

    /**
     * Mês de início da permissão de antecipação do 13º
     */
    public Month thirteenthSalaryAnticipationBegin;

    /**
     * Mês de fim da permissão de antecipação do 13º
     */
    public Month thirteenthSalaryAnticipationEnd;

    /**
     * Permitir o abono de férias aos colaboradores, respeitando a quantidade máxima (10 dias) ou 1/3 do período de férias solicitado
     */
    public Boolean allowVacationBonus;

    /**
     * Mês início da permissão de abono de férias
     */
    public Month vacationBonusBegin;

    /**
     * Mês fim da permissão de abono de férias
     */
    public Month vacationBonusEnd;

    /**
     * Tipo de abono permitido
     */
    public VacationBonusType vacationBonusType;

    /**
     * Quantidade mínima de dias de antecedência da solicitação de férias
     */
    public Long vacationSchedulingInAdvanceMinDays;

    /**
     * Permite o fracionamento de férias
     */
    public Boolean allowFractioning;

    /**
     * Tipos de fracionamento permitidos
     */
    public java.util.List<VacationPolicyFractioning> fractioningTypes;

    /**
     * Permite que o colaborador solicite férias com início anterior ao período concessivo
     */
    public Boolean allowBeforeConcessivePeriod = false;

    /**
     * Permite que o colaborador solicite férias superiores ao saldo do período aquisitivo em curso
     */
    public Boolean allowOverVacationBalance = false;

    private Map<String, Object> custom;

    /**
     * Atribui um valor a um campo customizado
     * @param field Nome do campo
     * @param value Valor do campo
     */
    public void setCustom(String field, Object value) {
        if (custom == null) {
            custom = new HashMap<>();
        }
        custom.put(field, value);
    }

    /**
     * Retorna o valor de um campo customizado
     *
     * @param field Nome do campo
     * @return Valor do campo, ou <code>null</code> se o valor do campo não estiver presente
     */
    public Object getCustom(String field) {
        if (custom != null) {
            return custom.get(field);
        }
        return custom;
    }

    /**
     * Retorna todos os campos customizados mapeados
     *
     * @return {@link Map} com os campos customizados
     */
    public Map<String, Object> getCustom() {
        return custom != null ? Collections.unmodifiableMap(custom) : Collections.emptyMap();
    }


}
