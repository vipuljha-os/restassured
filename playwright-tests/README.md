# Banana Club CRM - Playwright Functional Tests

Automated functional tests for Banana Club CRM application using Playwright with JavaScript.

## Prerequisites

- Node.js 18+
- npm or yarn

## Installation

```bash
cd playwright-tests
npm install
npx playwright install
```

## Configuration

1. Copy `.env.example` to `.env`:
```bash
cp .env.example .env
```

2. Update the `.env` file with your test credentials:
```
BASE_URL=https://bananaclub.int.kapturecrm.com
TEST_USERNAME=your_username
TEST_PASSWORD=your_password
```

## Running Tests

### Run all tests
```bash
npm test
```

### Run tests with browser visible
```bash
npm run test:headed
```

### Run tests with UI mode
```bash
npm run test:ui
```

### Run tests in debug mode
```bash
npm run test:debug
```

### Run specific test suites
```bash
npm run test:login      # Login tests only
npm run test:tickets    # Ticket management tests
npm run test:orders     # Order management tests
```

### View test report
```bash
npm run test:report
```

## Project Structure

```
playwright-tests/
├── tests/                    # Test specifications
│   ├── login.spec.js        # Login functionality tests
│   ├── tickets.spec.js      # Ticket management tests
│   ├── ticketDetails.spec.js # Ticket details tests
│   └── orders.spec.js       # Order management tests
├── pages/                    # Page Object Models
│   ├── LoginPage.js         # Login page object
│   ├── TicketsPage.js       # Tickets list page object
│   ├── TicketDetailsPage.js # Ticket details page object
│   ├── OrdersPage.js        # Orders page object
│   └── index.js             # Page exports
├── utils/                    # Test utilities
│   ├── fixtures.js          # Test fixtures with page objects
│   └── testHelpers.js       # Helper functions
├── test-data/               # Test data
│   └── testData.js          # Test data constants
├── playwright.config.js     # Playwright configuration
├── package.json             # Dependencies
└── .env.example             # Environment template
```

## Test Coverage

### Login Tests (12 tests)
- Login page load verification
- Valid credentials login
- Invalid credentials handling
- Empty field validation
- Session persistence
- Logout functionality

### Ticket Management Tests (20 tests)
- Pending tickets list
- Complete tickets list
- Junk tickets list
- Assigned to me tickets
- Ticket search
- Ticket dispose/reopen/junk operations
- Pagination
- Response time validation

### Ticket Details Tests (13 tests)
- Ticket information display
- Customer information
- Conversation history
- Canned responses
- Ticket actions (dispose, reopen, escalate)
- Attachments
- Navigation

### Order Management Tests (13 tests)
- Order list display
- Order search
- Order filters (status, date range)
- Order details
- Pagination
- Response time validation

## Writing New Tests

1. Create a new spec file in `tests/` directory
2. Import fixtures from `utils/fixtures.js`
3. Use page objects from `pages/` directory

Example:
```javascript
const { test, expect } = require('../utils/fixtures');

test.describe('My Feature', () => {
  test('should do something', async ({ loginPage, ticketsPage }) => {
    await loginPage.goto();
    await loginPage.loginWithTestUser();
    // ... test logic
  });
});
```

## Best Practices

1. Use Page Object Model for maintainability
2. Keep tests independent and isolated
3. Use fixtures for common setup
4. Add meaningful assertions
5. Include response time validations
6. Use descriptive test names with TC IDs

## Troubleshooting

### Tests failing due to timeout
- Increase timeout in `playwright.config.js`
- Check network connectivity to the test environment

### Login issues
- Verify credentials in `.env` file
- Check if the test environment is accessible

### Element not found
- Update locators in page objects if UI has changed
- Use Playwright's codegen to find new selectors:
  ```bash
  npx playwright codegen https://bananaclub.int.kapturecrm.com
  ```
