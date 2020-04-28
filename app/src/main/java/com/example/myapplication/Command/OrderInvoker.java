package com.example.myapplication.Command;

public class OrderInvoker {
        Command command;

        public OrderInvoker(Command command) {
            this.command = command;
        }

        public void setCommand(Command command) {
            this.command = command;
        }

        public String invoke() {
            return command.execute();
        }
}
