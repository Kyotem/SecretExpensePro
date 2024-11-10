import React from "react";
import "./ViewClaimTableRow.css";

const ViewClaimTableRow = ({ claim }) => {
  return (
    <div className="claim-row">
      <div className="claim-field id">{claim.id}</div>
      <div className="claim-field title">{claim.title}</div>
      <div className="claim-field amount">€{claim.amount}</div>
      <div className="claim-field description">{claim.description}</div>
    </div>
  );
};

export default ViewClaimTableRow;
