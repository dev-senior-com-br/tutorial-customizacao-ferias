package br.com.senior.custom.vacation.model;

/**
 * Mensagem de erro contendo o enum do tipo de erro e a mensagem
 */
public class ValidationMessage {

    /**
     * Tipo de mensagem de erro
     */
    public ValidationMessageType type;
    
    /**
     * Mensagem de erro
     */
    public String message;
    
}
