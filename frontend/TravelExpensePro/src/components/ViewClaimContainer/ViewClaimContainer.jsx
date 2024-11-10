import React from "react";
import ViewClaimInfo from "../ViewClaimInfo/ViewClaimInfo.jsx";
import "./ViewClaimContainer.css";

const ViewClaimContainer = ({ totalClaims, totalAmount }) => {
  return (
    <div className="view-claim-container">
      <h1>All Expense Claims</h1>
      <ViewClaimInfo totalClaims={totalClaims} totalAmount={totalAmount} />
    </div>
  );
};

export default ViewClaimContainer;
