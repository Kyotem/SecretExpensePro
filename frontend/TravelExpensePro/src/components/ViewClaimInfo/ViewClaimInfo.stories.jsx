import React from 'react';
import ViewClaimInfo from './ViewClaimInfo';
import { within, userEvent } from '@storybook/testing-library';
import { expect } from '@storybook/jest';
import './ViewClaimInfo.css';
import '../../global.css';

const meta = {
  component: ViewClaimInfo,
  title: 'Components/ViewClaimInfo',
};

export default meta;

// Default Story
export const Default = () => (
  <div style={{ maxWidth: '400px', margin: '0 auto' }}>
    <ViewClaimInfo totalClaims={20} totalAmount={300.00} />
  </div>
);

// Story with 0=no claims
export const NoClaims = () => (
  <div style={{ maxWidth: '400px', margin: '0 auto' }}>
    <ViewClaimInfo totalClaims={0} totalAmount={0.00} />
  </div>
);

// Story with large amount of claims
export const LargeNumberOfClaims = () => (
  <div style={{ maxWidth: '400px', margin: '0 auto' }}>
    <ViewClaimInfo totalClaims={1000} totalAmount={50000.00} />
  </div>
);

// Tests

// Default Story Test
Default.play = async ({ canvasElement }) => {
  const canvas = within(canvasElement);

  expect(canvas.getByText("Total Claims: 20")).toBeInTheDocument();
  expect(canvas.getByText("Total Cost: €300")).toBeInTheDocument();
};

// No Claims Test
NoClaims.play = async ({ canvasElement }) => {
  const canvas = within(canvasElement);

  expect(canvas.getByText("Total Claims: 0")).toBeInTheDocument();
  expect(canvas.getByText("Total Cost: €0")).toBeInTheDocument();
};

// Large Number of Claims Test
LargeNumberOfClaims.play = async ({ canvasElement }) => {
  const canvas = within(canvasElement);

  expect(canvas.getByText("Total Claims: 1000")).toBeInTheDocument();
  expect(canvas.getByText("Total Cost: €50000")).toBeInTheDocument();
};