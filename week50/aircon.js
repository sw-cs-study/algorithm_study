function solution(temperature, t1, t2, a, b, onboard) {
  let N = onboard.length;
  let M = 51;

  const dp = [...Array(N)].map(() => Array(M).fill(Infinity)); // dp[시간][온도] = 최저 전력

  dp[0][temperature + 10] = 0;
  for (let i = 1; i < N; i++) {
    for (let j = 0; j < M; j++) {
      let curTemp = j - 10; // -10~40
      // onboard[i]가 1인 경우 t1~t2만 진행함.
      if (onboard[i] === 1 && (curTemp < t1 || curTemp > t2)) continue;
      // 3가지 경우를 봄 => 1.에어컨off 2.유지 3.에어컨켜서낮춤
      // 1. 에어컨 off, 실외온도에 맞춰서 +- 1
      if (temperature === curTemp)
        dp[i][j] = Math.min(
          dp[i][j],
          dp[i - 1][j],
          j + 1 < M ? dp[i - 1][j + 1] : Infinity,
          j - 1 >= 0 ? dp[i - 1][j - 1] : Infinity,
        );
      else if (curTemp < temperature && j - 1 >= 0)
        dp[i][j] = Math.min(dp[i][j], dp[i - 1][j - 1]);
      else if (curTemp > temperature && j + 1 < M)
        dp[i][j] = Math.min(dp[i][j], dp[i - 1][j + 1]);
      // 2. 유지 (b)
      dp[i][j] = Math.min(dp[i][j], dp[i - 1][j] + b);
      // 3. 에어컨 on (a)
      if (j + 1 < M) dp[i][j] = Math.min(dp[i][j], dp[i - 1][j + 1] + a);
      if (j - 1 >= 0) dp[i][j] = Math.min(dp[i][j], dp[i - 1][j - 1] + a);
    }
  }
  console.log(dp);
  let answer = Math.min(...dp[N - 1]);

  return answer;
}
