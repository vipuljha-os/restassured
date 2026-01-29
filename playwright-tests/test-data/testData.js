// @ts-check

/**
 * Test data for Banana Club CRM testing
 */

const testUsers = {
  validUser: {
    username: process.env.TEST_USERNAME || 'bananaclub_test',
    password: process.env.TEST_PASSWORD || 'Testing@1234'
  },
  invalidUser: {
    username: 'invalid_user_12345',
    password: 'WrongPassword123!'
  },
  emptyCredentials: {
    username: '',
    password: ''
  }
};

const ticketData = {
  newTicket: {
    title: 'Test Ticket - QA Automation',
    description: 'This is a test ticket created by QA automation',
    priority: 'High',
    type: 'Query'
  },
  disposeRemarks: {
    resolved: 'Issue resolved - QA Test',
    notRequired: 'No action required - QA Test',
    duplicate: 'Duplicate ticket - QA Test'
  },
  reopenReasons: {
    customerRequest: 'Customer requested reopen - QA Test',
    additionalInfo: 'Additional information needed - QA Test',
    incompleteResolution: 'Incomplete resolution - QA Test'
  }
};

const orderData = {
  sampleOrders: {
    orderId: '1234567890',
    customerCode: '12345678'
  },
  orderStatuses: ['pending', 'processing', 'shipped', 'delivered', 'cancelled'],
  orderTypes: ['BB', 'BBNOW', 'EXPRESS']
};

const apiEndpoints = {
  ticketList: '/api/version3/ticket/get-ticket-list',
  addTicket: '/api/version3/ticket/add-ticket',
  ticketDetail: '/api/version3/ticket/get-ticket-detail',
  disposeTicket: '/api/version3/ticket/dispose-task',
  reopenTicket: '/api/version3/ticket/reopen-task',
  junkTicket: '/api/version3/ticket/junk-task',
  cannedResponse: '/api/version3/ticket/get-social-media-canned-response',
  orderList: '/ms/ticketcustomer/order/list',
  orderDetail: '/ms/ticketcustomer/order/detail'
};

const ticketStatuses = {
  PENDING: 'P',
  COMPLETE: 'C',
  JUNK: 'J'
};

const ticketTypes = {
  ASSIGNED_TO_ME: '2',
  CREATED_BY_ME: '3',
  UNASSIGNED: '1'
};

const responseThresholds = {
  pageLoad: 10000,        // 10 seconds
  apiResponse: 3000,      // 3 seconds
  ticketDetail: 5000,     // 5 seconds
  search: 5000            // 5 seconds
};

module.exports = {
  testUsers,
  ticketData,
  orderData,
  apiEndpoints,
  ticketStatuses,
  ticketTypes,
  responseThresholds
};
