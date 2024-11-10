// ErrorBox.stories.js
import React from "react";
import ErrorBox from "./ErrorBox";

export default {
  title: "Components/ErrorBox",
  component: ErrorBox,
  argTypes: {
    message: { control: 'text' },
  },
};

const Template = (args) => <ErrorBox {...args} />;

export const Default = Template.bind({});
Default.args = {
  message: "This is an error message!",
};
