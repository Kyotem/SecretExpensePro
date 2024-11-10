import React, { useEffect, useState } from "react";
import ViewClaimContainer from "../../components/ViewClaimContainer/ViewClaimContainer.jsx";
import ErrorBox from '../../components/errorbox/errorbox.jsx';
import Header from '../../components/Header/Header.jsx';
import "./ViewClaimPage.css";

const ViewClaimPage = () => {
    // States for claims data and error handling
    const [claims, setClaims] = useState([]);
    const [error, setError] = useState(null);

    // Fetch the claims data from the API when component mounts
    useEffect(() => {
        const fetchClaims = async () => {
            try {
                // Virtual Mocking server
                const response = await fetch("https://virtserver.swaggerhub.com/HELPERZAR_1/TEP_Claims_API/2.0.1/claims");

                if (!response.ok) {
                    // Handle specific HTTP errors
                    if (response.status === 401) {
                        throw new Error("Unauthorized access: Please log in to view your claims.");
                    } else if (response.status === 404) {
                        throw new Error("Claims data not found.");
                    } else {
                        throw new Error("An unexpected error occurred. Please try again later.");
                    }
                }

                const data = await response.json();
                setClaims(data);

            } catch (error) {
                // Manully check for standard fetch error and replace
                if (error.message === "Failed to fetch") {
                    setError("Network error: Unable to reach the server. Please check your connection.");
                } else {
                    setError(error.message);
                }
            }
        };

        fetchClaims();
    }, []);

    // Calculate total number of claims
    const totalClaims = claims.length;

    // Calculate total amount of claims (Using .toTixed() to limit to 2 decimal points)
    const totalAmount = claims.reduce((acc, claim) => acc + parseFloat(claim.amount), 0).toFixed(2);

    return (
        <div className="view-claim-page">
            <Header />
            {/* Conditional rendering for error message */}
            {error ? (
                <ErrorBox message={error} />
            ) : (
                <div>
                    <ViewClaimContainer totalClaims={totalClaims} totalAmount={totalAmount} />

                    {/* This section goes into ViewClaimContainer + Table and TableRow */}
                    <div className="claims-table">
                        {/* Render each claim */}
                        {claims.map(claim => (
                            <div key={claim.id} className="claim-item">
                                <p>Title: {claim.title}</p>
                                <p>Description: {claim.description}</p>
                                <p>Amount: €{claim.amount}</p>
                            </div>
                        ))}
                    </div>

                </div>
            )}
        </div>
    );
};

export default ViewClaimPage;
