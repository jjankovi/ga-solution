import { TestIbPage } from './app.po';

describe('test-ib App', function() {
  let page: TestIbPage;

  beforeEach(() => {
    page = new TestIbPage();
  });

  it('should display message saying app works', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('app works!');
  });
});
