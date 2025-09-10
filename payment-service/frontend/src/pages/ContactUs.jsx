export default function ContactUs() {
  const contactStyle = {
    maxWidth: "600px",
    margin: "4rem auto",
    padding: "1rem",
    backgroundColor: "white",
    borderRadius: "1rem",
    boxShadow: "0 2px 15px rgba(0,0,0,0.05)",
    textAlign: "center",
  };

  const headingStyle = {
    fontSize: "2rem",
    fontWeight: "700",
    marginBottom: "1rem",
    color: "#1f2937",
  };

  const listStyle = {
    listStyle: "none",
    padding: 0,
    fontSize: "1.1rem",
    color: "#4b5563",
  };

  const listItemStyle = {
    padding: "0.5rem 0",
  };

  return (
    <section style={contactStyle}>
      <h1 style={headingStyle}>Contact Us</h1>
      <p>Meet our dedicated team members:</p>
      <ul style={listStyle}>
        <li style={listItemStyle}>Virendra Pratap Singh</li>
        <li style={listItemStyle}>Aditya Shinde</li>
        <li style={listItemStyle}>Vishal Soni</li>
        <li style={listItemStyle}>Abhishek Dubey</li>
        <li style={listItemStyle}>Nawshika</li>
      </ul>
    </section>
  );
}
