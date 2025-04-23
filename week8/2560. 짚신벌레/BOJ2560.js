const input = require("fs")
  .readFileSync(process.platform === "linux" ? "/dev/stdin" : "sample.txt")
  .toString()
  .trim()
  .split("\n");

const [a, b, d, n] = input[0].split(" ").map(Number);

const dp = Array(n + 1).fill(0); // index 시간에 살아있는 짚신벌레 수
const prefixSum = Array(n + 1).fill(0);
dp[0] = 1;
prefixSum[0] = dp[0];
// d-1까지는 먼저 제작
for (let i = 1; i < d; i++) {
  dp[i] = 1;
  if (i - b >= 0) dp[i] -= prefixSum[i - b]; // 시작 구간 전까지 빼줌
  if (i - a >= 0) dp[i] += prefixSum[i - a]; // 누적합 값 더함
  else {
    for (let k = i - a; k >= 0; k--) {
      dp[i] += dp[k];
    }
  }
  // 0보다 작은건 1000나머지로 연산해서 발생하는거라 처리해줘야함
  if (dp[i] < 0) dp[i] += 1000;
  dp[i] = dp[i] % 1000;

  prefixSum[i] = (prefixSum[i - 1] + dp[i]) % 1000;
}
// d 부터 n까지 dp테이블 채우기
for (let i = d; i <= n; i++) {
  dp[i] = (prefixSum[i - a] - prefixSum[i - b]) % 1000;
  if (dp[i] < 0) dp[i] += 1000;
  prefixSum[i] = (prefixSum[i - 1] + dp[i]) % 1000;
}

// 값 구하기
console.log(dp[n]);
