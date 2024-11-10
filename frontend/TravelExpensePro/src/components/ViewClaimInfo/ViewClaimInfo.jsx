import React from "react";
import "./ViewClaimInfo.css";

const ViewClaimInfo = ({ totalClaims, totalAmount }) => {
  return (
    <div className="view-claim-info">
      <p>Total Claims: {totalClaims}</p>
      <p>Total Cost: €{totalAmount}</p>
    </div>
  );
};

export default ViewClaimInfo;
