import React from "react";
import ViewClaimTableRow from "../ViewCLaimTableRow/ViewClaimTableRow";
import "./ViewClaimTable.css";

const ViewClaimTable = ({ claims }) => {
    return (
      <div className="claims-table">
        <div className="table-header">
          <div className="header-field id">ID</div>
          <div className="header-field title">Title</div>
          <div className="header-field amount">Amount (€)</div>
          <div className="header-field description">Description</div>
        </div>
        {claims.map(claim => (
          <ViewClaimTableRow key={claim.id} claim={claim} />
        ))}
      </div>
    );
  };
  
  export default ViewClaimTable;