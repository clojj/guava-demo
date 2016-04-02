package jwin.pojo;

import com.google.common.base.Objects;

public class Input {
    private String input;

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Input input1 = (Input) o;
        return Objects.equal(input, input1.input);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(input);
    }
}
