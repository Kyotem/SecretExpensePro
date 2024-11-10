import React from 'react';
import { within } from '@storybook/testing-library';
import { expect } from '@storybook/jest';
import ViewClaimTable from './ViewClaimTable';
import './ViewClaimTable.css';
import '../../global.css';

const meta = {
  component: ViewClaimTable,
  title: 'Components/ViewClaimTable',
};

export default meta;

// Default Story
export const Default = () => (
  <div style={{ maxWidth: '800px', margin: '0 auto' }}>
    <ViewClaimTable claims={[
      { id: '1', title: 'Taxi Fare', description: 'Taxi from airport', amount: '45.75' },
      { id: '2', title: 'Hotel Stay', description: 'Stay at Hilton', amount: '150.00' },
      { id: '3', title: 'Meals', description: 'Lunch for meeting', amount: '30.00' }
    ]} />
  </div>
);

// No Claims Story
export const NoClaims = () => (
  <div style={{ maxWidth: '800px', margin: '0 auto' }}>
    <ViewClaimTable claims={[]} />
  </div>
);

// Large Number of Claims Story
export const LargeNumberOfClaims = () => (
  <div style={{ maxWidth: '800px', margin: '0 auto' }}>
    <ViewClaimTable claims={[
      { id: '1', title: 'Taxi Fare', description: 'Taxi from airport', amount: '45.75' },
      { id: '2', title: 'Hotel Stay', description: 'Stay at Hilton', amount: '150.00' },
      { id: '3', title: 'Meals', description: 'Lunch for meeting', amount: '30.00' },
      { id: '4', title: 'Transport', description: 'Bus tickets', amount: '12.00' },
      { id: '5', title: 'Conference Fee', description: 'Tech conference registration', amount: '200.00' },
    ]} />
  </div>
);

// Tests

// Default Story Test
Default.play = async ({ canvasElement }) => {
  const canvas = within(canvasElement);

  // Check that headers are present
  expect(canvas.getByText("ID")).toBeInTheDocument();
  expect(canvas.getByText("Title")).toBeInTheDocument();
  expect(canvas.getByText("Amount (€)")).toBeInTheDocument();
  expect(canvas.getByText("Description")).toBeInTheDocument();

  // Check that claim values are rendered properly, matching the title, amount, and description
  expect(canvas.getByText("Taxi Fare")).toBeInTheDocument();
  expect(canvas.getByText(/45\.75/)).toBeInTheDocument(); 
  expect(canvas.getByText("Taxi from airport")).toBeInTheDocument();
};

// Large Number of Claims Test
LargeNumberOfClaims.play = async ({ canvasElement }) => {
  const canvas = within(canvasElement);

  // Defining claims to check
  const claims = [
    { title: 'Taxi Fare', amount: '45.75' },
    { title: 'Hotel Stay', amount: '150.00' },
    { title: 'Meals', amount: '30.00' },
    { title: 'Transport', amount: '12.00' },
    { title: 'Conference Fee', amount: '200.00' },
  ];

  // Check that each claim's title and amount is rendered properly
  claims.forEach(claim => {
    expect(canvas.getByText(claim.title)).toBeInTheDocument();  
    expect(canvas.getByText(new RegExp(`€\\s*${claim.amount}`))).toBeInTheDocument();  
  });
};
