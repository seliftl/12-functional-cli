package ohm.softa.a12.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Wrapper object for ICNDB API results
 * Results of the API always contain a value and a success indicator
 * to handle this wrapping this class models the same structure to ease the deserialization
 * @author Peter Kurfer
 */
public class ResponseWrapper<T> {

    /**
     * success indicator: error or success
     */
    private String type;

    /**
     * actual received value
     */
    private T value;

    public ResponseWrapper() {
        /* set default to error
         * to avoid calls to the value when there is no value */
        type = "error";
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public boolean isSuccessfull() {
        return type != null && type.equals("success");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof ResponseWrapper)) return false;

        var that = (ResponseWrapper<?>) o;

        return new EqualsBuilder()
                .append(getType(), that.getType())
                .append(getValue(), that.getValue())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getType())
                .append(getValue())
                .toHashCode();
    }

}
