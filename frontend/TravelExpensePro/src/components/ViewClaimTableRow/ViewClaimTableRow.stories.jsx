import React from 'react';
import { within } from '@storybook/testing-library';
import { expect } from '@storybook/jest';
import ViewClaimTableRow from './ViewClaimTableRow';
import './ViewClaimTableRow.css';
import '../../global.css';

const meta = {
  component: ViewClaimTableRow,
  title: 'Components/ViewClaimTableRow',
};

export default meta;

// Default Story
export const Default = () => (
  <div style={{ maxWidth: '800px', margin: '0 auto' }}>
    <ViewClaimTableRow claim={{
      id: '1', 
      title: 'Taxi Fare', 
      description: 'Taxi from airport', 
      amount: '45.75'
    }} />
  </div>
);

// Tests

// Default Story Test
Default.play = async ({ canvasElement }) => {
  const canvas = within(canvasElement);

  expect(canvas.getByText("Taxi Fare")).toBeInTheDocument();
  expect(canvas.getByText("Taxi from airport")).toBeInTheDocument();
  
  // Use regex to match the amount in the format €45.75 (Thank you storybook)
  expect(canvas.getByText(/€\s*45\.75/)).toBeInTheDocument(); 
  
  expect(canvas.getByText("1")).toBeInTheDocument(); 
};
