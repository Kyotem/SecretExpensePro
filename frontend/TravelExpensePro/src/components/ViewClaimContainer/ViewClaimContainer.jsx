import React from "react";
import ViewClaimInfo from "../ViewClaimInfo/ViewClaimInfo.jsx";
import ViewClaimTable from "../ViewClaimTable/ViewClaimTable.jsx";
import "./ViewClaimContainer.css";

const ViewClaimContainer = ({ totalClaims, totalAmount, claims }) => {
  return (
    <div className="view-claim-container">
      <h1>All Expense Claims</h1>
      <ViewClaimInfo totalClaims={totalClaims} totalAmount={totalAmount} />
      <ViewClaimTable claims={claims} />
    </div>
  );
};

export default ViewClaimContainer;
