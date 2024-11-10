import React from 'react';
import ViewClaimPage from './ViewClaimPage';
import { within, userEvent } from '@storybook/testing-library';
import { expect } from '@storybook/jest';

export default {
  title: 'Pages/ViewClaimPage',
  component: ViewClaimPage,
  parameters: {
    layout: 'fullscreen',
  },
};

// Mock fetch for Default state
const mockClaimsData = [
  { id: "1", title: "Taxi from airport to hotel", description: "Taxi fare from Schiphol Airport to city center hotel", amount: "45.75" },
  { id: "2", title: "Hotel stay for business meeting", description: "One-night stay at Hilton Hotel for client meeting", amount: "150.00" },
];

// Mock fetch function
const fetchMock = (data, error = null) => {
  global.fetch = jest.fn(() =>
    Promise.resolve({
      ok: !error,
      status: error ? 500 : 200,
      json: () => error ? Promise.reject(error) : Promise.resolve(data),
    })
  );
};

// Default Story: Simulates successful data fetch
export const Default = {
  args: {},
  play: async ({ canvasElement }) => {
    fetchMock(mockClaimsData);  // Mock fetch for successful data fetch
    const canvas = within(canvasElement);
    await expect(canvas.findByText('Total Claims: 2')).resolves.toBeInTheDocument();
    await expect(canvas.findByText('Total Cost: €195.75')).resolves.toBeInTheDocument();
  },
};

// Loading State: Simulates loading state by delaying the fetch
export const Loading = {
  args: {},
  play: async ({ canvasElement }) => {
    global.fetch = jest.fn(() => new Promise(resolve => setTimeout(resolve, 3000)));
    const canvas = within(canvasElement);
    await expect(canvas.getByText('Loading...')).toBeInTheDocument();
  },
};

// FIXME: Does not work Right Now
// Error State: Simulates fetch error
export const Error = {
  args: {},
  play: async ({ canvasElement }) => {
    fetchMock(null, "Network error: Unable to reach the server.");
    const canvas = within(canvasElement);
    await expect(canvas.findByText('Network error: Unable to reach the server.')).resolves.toBeInTheDocument();
  },
};

// TODO: Implement tests