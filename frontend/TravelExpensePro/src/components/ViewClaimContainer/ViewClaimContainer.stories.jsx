import React from "react";
import ViewClaimContainer from "./ViewClaimContainer";
import { within } from "@storybook/testing-library";
import { expect } from "@storybook/jest";

const meta = {
  component: ViewClaimContainer,
  title: "Components/ViewClaimContainer",
};

export default meta;

const claimsData = [
  { id: "1", title: "Taxi Fare", description: "Taxi from airport", amount: "45.75" },
  { id: "2", title: "Hotel Stay", description: "Stay at Hilton", amount: "150.00" },
  { id: "3", title: "Meals", description: "Lunch for meeting", amount: "30.00" },
  { id: "4", title: "Transport", description: "Bus tickets", amount: "12.00" },
  { id: "5", title: "Conference Fee", description: "Tech conference registration", amount: "200.00" },
];

// Default Story
export const Default = () => (
  <div style={{ maxWidth: '800px', margin: '0 auto' }}>
    <ViewClaimContainer
      totalClaims={claimsData.length}
      totalAmount={claimsData.reduce((acc, claim) => acc + parseFloat(claim.amount), 0).toFixed(2)}
      claims={claimsData}
    />
  </div>
);

// No Claims Story
export const NoClaims = () => (
  <div style={{ maxWidth: '800px', margin: '0 auto' }}>
    <ViewClaimContainer totalClaims={0} totalAmount="0.00" claims={[]} />
  </div>
);

// Large Number of Claims Story
export const LargeNumberOfClaims = () => (
  <div style={{ maxWidth: '800px', margin: '0 auto' }}>
    <ViewClaimContainer
      totalClaims={100}
      totalAmount="1200.00"
      claims={Array.from({ length: 100 }, (_, index) => ({
        id: `${index + 1}`,
        title: `Claim #${index + 1}`,
        description: `Description for Claim #${index + 1}`,
        amount: `${(Math.random() * 100).toFixed(2)}`,
      }))}
    />
  </div>
);

// Default Story Test
Default.play = async ({ canvasElement }) => {
  const canvas = within(canvasElement);
  // Checking that total claims and amounts are correctly displayed
  expect(canvas.getByText("Total Claims: 5")).toBeInTheDocument();
  expect(canvas.getByText("Total Cost: €437.75")).toBeInTheDocument();
  claimsData.forEach(claim => {
    expect(canvas.getByText(claim.title)).toBeInTheDocument();
    expect(canvas.getByText(new RegExp(`€\\s*${claim.amount}`))).toBeInTheDocument();
  });
};

// No Claims Test
NoClaims.play = async ({ canvasElement }) => {
  const canvas = within(canvasElement);
  expect(canvas.getByText("Total Claims: 0")).toBeInTheDocument();
  expect(canvas.getByText("Total Cost: €0.00")).toBeInTheDocument();
};

// Large Number of Claims Test
LargeNumberOfClaims.play = async ({ canvasElement }) => {
  const canvas = within(canvasElement);
  // Checking the first claim as an example
  expect(canvas.getByText("Claim #1")).toBeInTheDocument();
  expect(canvas.getByText(/€\s*\d+\.\d{2}/)).toBeInTheDocument(); // Checking the first claim amount
};
