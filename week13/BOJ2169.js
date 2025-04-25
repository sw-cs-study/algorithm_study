/**
 * 로봇 조종하기
 * 왼, 아래, 오른 만 이동하므로 바텀업 dp로 해보자
 * 현재 위치에 영향을 주는 값 => 왼, 위, 오른
 * 1. 첫줄은 왼쪽에서 오른쪽으로 이동하게 먼저처리
 * 2. 2번째 줄부터 줄을 하나 복사함(하나는 왼쪽에서 오른쪽으로 왼쪽과 위쪽 중 최댓값, 하나는 오른쪽에서 왼쪽으로 오른쪽과 위쪽 중 최댓값)
 * 3. 값 비교 후 dp 테이블에 넣기
 * 4. n * (2n) => O(n^2) => 10 ^ 6
 */
const input = require("fs")
  .readFileSync(process.platform === "linux" ? "/dev/stdin" : "../sample.txt")
  .toString()
  .trim()
  .split("\n");

const [n, m] = input[0].split(" ").map(Number);
const dp = [];
for (let i = 0; i < n; i++) {
  dp.push(input[i + 1].split(" ").map(Number));
}

// first line
for (let j = 1; j < m; j++) {
  dp[0][j] += dp[0][j - 1];
}
// other lines
for (let i = 1; i < n; i++) {
  const left = new Array(m).fill(0);
  const right = new Array(m).fill(0);

  // left to right
  left[0] = dp[i][0] + dp[i - 1][0];
  for (let j = 1; j < m; j++) {
    left[j] = Math.max(left[j - 1], dp[i - 1][j]) + dp[i][j];
  }

  // right to left
  right[m - 1] = dp[i][m - 1] + dp[i - 1][m - 1];
  for (let j = m - 2; j >= 0; j--) {
    right[j] = Math.max(right[j + 1], dp[i - 1][j]) + dp[i][j];
  }
  // update dp table
  for (let j = 0; j < m; j++) {
    dp[i][j] = Math.max(left[j], right[j]);
  }
}
console.log(dp[n - 1][m - 1]);
