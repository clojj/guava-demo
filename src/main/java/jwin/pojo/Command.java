package jwin.pojo;

import com.google.common.base.Objects;

public class Command {
    private String command;

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Command command1 = (Command) o;
        return Objects.equal(command, command1.command);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(command);
    }
}
