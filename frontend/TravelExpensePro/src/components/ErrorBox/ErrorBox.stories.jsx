// ErrorBox.stories.js
import React from "react";
import ErrorBox from "./ErrorBox";
import './ErrorBox.css';
import { within, userEvent } from "@storybook/testing-library";
import { expect } from "@storybook/jest";
import '../../global.css';


export default {
  title: "Components/ErrorBox",
  component: ErrorBox,
  argTypes: {
    message: { control: 'text' },
  },
};

const Template = (args) => <ErrorBox {...args} />;

// Default story
export const Default = Template.bind({});
Default.args = {
  message: "This is an error message!",
};

// Story with a longer message
export const LongMessage = Template.bind({});
LongMessage.args = {
  message: "This is a longer error message to demonstrate how the error box handles more content inside the container.",
};

// Story with no message
export const EmptyMessage = Template.bind({});
EmptyMessage.args = {
  message: "",
};

// Tests

// Default test
Default.play = async ({ canvasElement }) => {
  const canvas = within(canvasElement);

  expect(
    canvas.getByText("This is an error message!")
  ).toBeInTheDocument();
};

// Test for long message
LongMessage.play = async ({ canvasElement }) => {
  const canvas = within(canvasElement);

  expect(
    canvas.getByText("This is a longer error message to demonstrate how the error box handles more content inside the container.")
  ).toBeInTheDocument();
};
