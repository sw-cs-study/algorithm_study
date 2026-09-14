function solution(depth, money, excavate) {
  const n = depth.length;

  // dp[l][r]
  // l ~ r 구간에서 보물이 어디 있든
  // 반드시 찾기 위해 필요한 최소 비용
  const dp = Array.from({ length: n }, () => Array(n).fill(0));

  // 해당 구간에서 처음 파야 할 위치
  const pick = Array.from({ length: n }, () => Array(n).fill(0));

  // 구간 길이 1
  for (let i = 0; i < n; i++) {
    dp[i][i] = depth[i];
    pick[i][i] = i;
  }

  // 구간 길이 2부터
  for (let len = 2; len <= n; len++) {
    for (let l = 0; l + len - 1 < n; l++) {
      const r = l + len - 1;

      dp[l][r] = Infinity;

      // k를 처음 파는 경우
      for (let k = l; k <= r; k++) {
        const left = k > l ? dp[l][k - 1] : 0;

        const right = k < r ? dp[k + 1][r] : 0;

        // 상대가 더 불리한 방향으로 보물을 둔다고 생각
        const cost = depth[k] + Math.max(left, right);

        if (cost < dp[l][r]) {
          dp[l][r] = cost;
          pick[l][r] = k;
        }
      }
    }
  }

  // 문제에서 money가 최소 보장 비용 이상이라고 했으므로
  // dp[0][n - 1] <= money가 보장됨

  let l = 0;
  let r = n - 1;

  while (l <= r) {
    const k = pick[l][r];

    // excavate는 1번 열부터 시작
    const result = excavate(k + 1);

    if (result === 0) {
      return k + 1;
    }

    if (result === -1) {
      // 보물이 왼쪽
      r = k - 1;
    } else {
      // 보물이 오른쪽
      l = k + 1;
    }
  }
}
