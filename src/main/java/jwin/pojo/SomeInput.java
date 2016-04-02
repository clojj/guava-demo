package jwin.pojo;

import com.google.common.base.Objects;

public class SomeInput {

    private String id;
    private Command cmd;
    private Input input;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Command getCmd() {
        return cmd;
    }

    public void setCmd(Command cmd) {
        this.cmd = cmd;
    }

    public Input getInput() {
        return input;
    }

    public void setInput(Input input) {
        this.input = input;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SomeInput someInput = (SomeInput) o;
        return Objects.equal(id, someInput.id) &&
                Objects.equal(cmd, someInput.cmd) &&
                Objects.equal(input, someInput.input);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, cmd, input);
    }
}
