import React from "react";
import ViewClaimPage from "./ViewClaimPage";
import { within } from "@storybook/testing-library";
import { expect } from "@storybook/jest"; 
import './ViewClaimPage.css';
import '../../global.css';

const meta = {
  component: ViewClaimPage,
  title: "Pages/ViewClaimPage",
};

export default meta;

// Mocking the fetch call in the ViewClaimPage component
global.fetch = async () => {
  return {
    ok: true,
    json: async () => [
      { id: "1", title: "Taxi Fare", description: "Taxi from airport", amount: "45.75" },
      { id: "2", title: "Hotel Stay", description: "Stay at Hilton", amount: "150.00" },
      { id: "3", title: "Meals", description: "Lunch for meeting", amount: "30.00" },
      { id: "4", title: "Transport", description: "Bus tickets", amount: "12.00" },
      { id: "5", title: "Conference Fee", description: "Tech conference registration", amount: "200.00" },
    ]
  };
};

// Default Story
export const Default = () => <ViewClaimPage />;

// Error Story - Simulating a network error
export const NetworkError = () => {
  global.fetch = async () => {
    return Promise.reject(new Error("Network error"));
  };
  return <ViewClaimPage />;
};

// No Claims Story - Simulating empty claims data
export const NoClaims = () => {
  global.fetch = async () => {
    return {
      ok: true,
      json: async () => [],
    };
  };
  return <ViewClaimPage />;
};

// Default Story Test
Default.play = async ({ canvasElement }) => {
  const canvas = within(canvasElement);

  expect(canvas.getByText("Total Claims: 5")).toBeInTheDocument();
  expect(canvas.getByText("Total Cost: €437.75")).toBeInTheDocument();
  expect(canvas.getByText("Taxi Fare")).toBeInTheDocument();
  expect(canvas.getByText("€45.75")).toBeInTheDocument();
};

// Network Error Test
NetworkError.play = async ({ canvasElement }) => {
  const canvas = within(canvasElement);
  expect(canvas.getByText("Network error: Unable to reach the server. Please check your connection.")).toBeInTheDocument();
};

// No Claims Test
NoClaims.play = async ({ canvasElement }) => {
  const canvas = within(canvasElement);
  expect(canvas.getByText("Total Claims: 0")).toBeInTheDocument();
  expect(canvas.getByText("Total Cost: €0.00")).toBeInTheDocument();
  expect(canvas.getByText("No claims found.")).toBeInTheDocument();
};
